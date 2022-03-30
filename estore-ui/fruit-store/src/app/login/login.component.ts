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

  

  constructor(private userService: UserService, private router: Router) { }

  ngOnInit(): void {
  }

  getUsers(name: string): void 
  {
    this.userService.getUser(name)
      .subscribe(user => {
        if (user) {
          if (user.username.normalize() === "admin".normalize()) {
            this.router.navigate(['/products']);
            return;
          }
          sessionStorage.setItem('currentUser', JSON.stringify(user));
          this.router.navigate(['/dashboard']);
        } else 
        {
          alert('User "' + name + '" not found please sign up');
        }
  
      });
  }
}
