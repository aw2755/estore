import { Component, OnInit } from '@angular/core';

import { User } from '../user';
import { UserService } from '../user.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
  users: User[] = [];

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.getUsers();
  }

  getUsers(): void {
    this.userService.getUsers()
    .subscribe(users => this.users = users);
  }

   add(name : string): void 
   {
     name = name.trim();
     const user : User = {
       username: name
     }
      if (!name) {return;}
      this.userService.addUser(user).subscribe((user) => {
         this.users.push(user);
       });
   }
}
