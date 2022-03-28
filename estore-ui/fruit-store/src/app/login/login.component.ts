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
  users: User[] = [];

  constructor(private userService: UserService, private router: Router) { }

  ngOnInit(): void {
  }

  getUsers(username: String): void {
    if (username == "admin") {
        this.router.navigateByUrl("/admin");
    }
    else
    {
        this.router.navigateByUrl("/user")
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

}
