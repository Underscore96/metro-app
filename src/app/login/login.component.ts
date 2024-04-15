import { Component } from '@angular/core';
import { FormControl, FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  uname: any;
pws: any;

username = "";
password = "";
errorMsg = "";
rememberMe = new FormControl(false)


constructor(private auth: AuthService, private router: Router) { }
  

ngOnInit(): void {
  this.auth.login(this.username, this.password)
  
 }

login() {
  if (this.username.trim().length === 0) {
    this.errorMsg = "Username is required";
  } else if (this.password.trim().length === 0) {
    this.errorMsg = "Password is required";
  } else {
    this.errorMsg = "";
    let res = this.auth.login(this.username, this.password);
    if (res === 200) {
      this.router.navigate(['admin']);
    }
    if (res === 403) {
      this.errorMsg = "Invalid Credentials";
    }
  }
}



}



