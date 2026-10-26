import { Component, OnInit } from '@angular/core';
import { InventoryService } from '../../service/inventory';
import { Product } from '../../product.model';
import { signal } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
@Component({
  selector: 'app-product-list',
  imports: [RouterLink, ReactiveFormsModule],
  templateUrl: './product-list.html',
  styleUrl: './product-list.css',
})
export class ProductList implements OnInit {
  products = signal<Product[]>([]);

  constructor(
    private productService: InventoryService,
    private router: Router,
    private route: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.productService
      .getProducts()
      .subscribe((data) => this.products.set(data));
  }

  onEdit(id: string) {
    this.router.navigate([id, 'edit'], { relativeTo: this.route });
  }

  onDelete(id: string) {
    if (!confirm('Delete this product')) return;
    this.productService.deleteProduct(id).subscribe(() => {
      this.products.update((list) => list.filter((p) => p.id !== id));
    });
  }
}
