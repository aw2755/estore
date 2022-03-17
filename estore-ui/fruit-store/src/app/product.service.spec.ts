import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import {HttpClientModule} from '@angular/common/http';
import { ProductService } from './product.service';
import { RouterTestingModule } from '@angular/router/testing';

describe('ProductService ', () => {
  let service: ProductService;

  beforeEach(() => TestBed.configureTestingModule({
    imports: [HttpClientTestingModule, RouterTestingModule], 
    providers: [ProductService]
  }));

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
