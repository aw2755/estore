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
<<<<<<< HEAD
      .subscribe(user => {
        if (user) {
          if (user.username.normalize() === "admin".normalize()) {
            this.router.navigate(['/products']);
            return;
          }
=======

      .subscribe(user => 
        {
        if (user) 
        {
>>>>>>> cc57f0a197a3f31a10e393941b2c191ecb144ea9
          sessionStorage.setItem('currentUser', JSON.stringify(user));
          this.router.navigate(['/dashboard']);
        } else 
        {
          alert('User "' + name + '" not found');
        }
  
<<<<<<< HEAD
      });
  }
}
=======
        });
   }
}
>>>>>>> cc57f0a197a3f31a10e393941b2c191ecb144ea9
