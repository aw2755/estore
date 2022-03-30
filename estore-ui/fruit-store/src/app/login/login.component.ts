import { Component, OnInit } from '@angular/core';

import { User } from '../user';
import { UserService } from '../user.service';

import { Router } from '@angular/router';
import { Product} from '../product';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  products: Product[] = [];
  currentUser!: User;

  constructor(private userService: UserService, private router: Router) { }

  ngOnInit(): void {
    try {
      this.currentUser = JSON.parse(sessionStorage['currentUser']);
      } catch (e: any) {
        this.router.navigate(['/login']);
      }
  }

  getUsers(name: string): void {
    this.userService.getUser(name)

      .subscribe(user => {
        if (user) {
          sessionStorage.setItem('currentUser', JSON.stringify(user));
          this.router.navigate(['/dashboard']);
        } else {
          alert('User not found');
        }
  
}
);
}
}
  // add(name: string): void {
  //   name = name.trim();
  //   if (!name) { return; }
  //   this.userService.addUser({ name } as User)
  //     .subscribe((user: User) => {
  //       this.users.push(user);
  //     });
  // }
//