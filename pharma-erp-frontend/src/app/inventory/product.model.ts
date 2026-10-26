// Matches ProductResponseDto — what the backend sends back
export interface Product {
  id: string; // UUID → string in JSON
  sku: string; // auto-generated, read-only
  name: string;
  genericName?: string; // nullable in DB → optional here
  brand: string;
  manufacturer: string;
  category?: string;
  hsnCode: string;
  unit?: string;
  packSize: number;
  mrp: number;
  //   active: boolean;          // soft delete flag
  createdAt?: string; // ISO timestamp string, if your DTO includes it
  updatedAt?: string;
}

// Matches ProductRequestDto — what the frontend sends to create/update
export interface ProductRequest {
  name: string;
  genericName?: string;
  brand: string;
  manufacturer: string;
  category?: string;
  hsnCode: string;
  unit?: string;
  packSize: number;
  mrp: number;
}
