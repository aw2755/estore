import { Component, OnInit } from '@angular/core';

import { User } from '../user';
import { UserService } from '../user.service';

import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  

  constructor(private userService: UserService, private router: Router) { }

  ngOnInit(): void {
  }

  getUsers(username: String): void {
    /**If username is admin than go to products*/
    this.userService.getUser(username)
      .subscribe(users => {
        if (users.username === 'admin') {
          this.router.navigateByUrl("/products");
        } 
        /**  check if username exist*/
        else if (users.username === username) {
          //sessionStorage.setItem('currentUser', JSON.stringify(users));
          
          this.router.navigateByUrl("/dashboard");
        }
        else {
          alert('User not found');
        }
      });
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