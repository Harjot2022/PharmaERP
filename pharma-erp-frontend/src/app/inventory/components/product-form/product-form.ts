import { Component, signal } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  Validators,
  ReactiveFormsModule,
} from '@angular/forms';
import { InventoryService } from '../../service/inventory';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-product-form',
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './product-form.html',
  styleUrl: './product-form.css',
})
export class ProductForm {
  productForm: FormGroup;
  submitting = signal(false);
  productId: string | null = null;
  isEditMode = signal(false);
  errorMessage = signal<string | null>(null);

  constructor(
    private fb: FormBuilder,
    private productService: InventoryService,
    private route: ActivatedRoute,
    private router: Router,
  ) {
    this.productForm = this.fb.group({
      name: ['', Validators.required],
      genericName: [''],
      brand: ['', Validators.required],
      manufacturer: ['', Validators.required],
      category: [''],
      hsnCode: ['', Validators.required],
      unit: [
        '',
        Validators.required,
        Validators.pattern(/^(STRIP|BOTTLE|BOX)$/),
      ],
      packSize: [0, [Validators.required, Validators.min(1)]],
      mrp: [null, [Validators.required, Validators.min(0)]],
      gst_rate: [null, [Validators.required]],
    });
  }

  ngOnInit() {
    this.productId = this.route.snapshot.paramMap.get('id');
    if (this.productId) {
      this.isEditMode.set(true);
      this.productService
        .getProductByID(this.productId)
        .subscribe((product) => {
          this.productForm.patchValue(product);
        });
    }
  }
  onSubmit() {
    console.log('onSubmit clicked! Is form invalid?', this.productForm.invalid);
    console.log('Form errors details:', this.productForm.errors);
    console.log('Form controls status:', this.productForm.controls);
    if (this.productForm.invalid) {
      this.productForm.markAllAsTouched();
      return;
    }
    console.log('Is Edit Mode?', this.isEditMode());
    console.log('Product ID being sent:', this.productId);
    console.log('Form Data being sent:', this.productForm.value);
    const request$ = this.isEditMode()
      ? this.productService.updateProduct(
          this.productId!,
          this.productForm.value,
        )
      : this.productService.addProduct(this.productForm.value);
    console.log('Firing network request...');
    request$.subscribe({
      next: (response) => {
        console.log('Server response success! Redirecting...', response);
        this.router.navigate(['/inventory/products']);
      },
      error: (err) => {
        console.log('error', err);
        this.errorMessage.set(err.error?.message ?? 'Save failed');
      },
    });
  }
}
