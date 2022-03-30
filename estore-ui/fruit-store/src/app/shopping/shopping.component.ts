import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Product } from '../product';
import { User } from '../user';
import { UserService } from '../user.service';

@Component({
  selector: 'app-shopping',
  templateUrl: './shopping.component.html',
  styleUrls: ['./shopping.component.css']
})
export class ShoppingComponent implements OnInit {

  cart: Product[] = [];
  currentUser!: User;

  constructor(private userService: UserService, private router : Router) { }

  ngOnInit(): void {
    this.currentUser = JSON.parse(sessionStorage['currentUser']);
    this.cart = this.currentUser.shoppingCat;
    this.getItems();
  }

  getItems(): void {
    this.userService.getAll(this.currentUser.username).subscribe(
      cart => this.cart = cart
    );
  }

  calculateTotal(): number {
    let total : number = 0;
    for (let i = 0; i < this.cart.length; i++) {
      total = total + this.cart[i].price;
    }
    return total;
  }

  remove(name : String): void {
    this.userService.removeProduct(this.currentUser.username, name).subscribe();
    location.reload();
    alert("removed " + name + " from cart");
  }

  checkout(): void 
  {
      this.userService.checkoutProducts(this.currentUser.username).subscribe();
      this.router.navigateByUrl("/dashboard");
      alert("You have successfully checked out, your total is: " + this.calculateTotal())
  }


}