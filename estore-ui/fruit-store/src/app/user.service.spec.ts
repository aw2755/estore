import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import {HttpClientModule} from '@angular/common/http';
import { UserService } from './user.service';
import { RouterTestingModule } from '@angular/router/testing';

describe('UserService', () => {
  let service: UserService;

  beforeEach(() => TestBed.configureTestingModule({
    imports: [HttpClientTestingModule, RouterTestingModule], 
    providers: [UserService]
  }));

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
