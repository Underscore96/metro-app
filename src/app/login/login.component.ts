import { Component, EventEmitter, Output } from '@angular/core';
import { FormControl, FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { LocalStorageService } from 'angular-web-storage';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
uname: any;
pws: any;

username = "";
password = "";
  errorMsg!: boolean;
utente: any
rememberMe: boolean = true;


constructor( private router: Router, private http: HttpClient, private localStorage: LocalStorageService) { }
  
@Output() Username = new EventEmitter();

ngOnInit(): void {
   if (this.localStorage.get('loggedInUser')) {
    this.router.navigate(['admin']);
  } else {
    this.onSubmit(this.username, this.password);
  }

  
 }


 baseUrl = 'http://localhost:8080/Metro/rest/login';





 onSubmit(username: string, password: string) {
  const url = `${this.baseUrl}?nomeUtente=${username}&password=${password}`;

  this.http.get<any[]>(url).subscribe(
    data => {
      if (data && data.length > 0) {
        console.log('Login successful.');
        this.router.navigate(['admin']);
        if (this.rememberMe) {
          this.localStorage.set('loggedInUser', { username: this.username, password: this.password });
        }
      } else {
        console.log('Invalid username or password. Access denied.');
      }
    },
    error => {
      console.error('Error during login:', error);
      this.errorMsg = true;
    }
  );
}



onRememberMeChange(): void {
  if (!this.rememberMe) {
    this.localStorage.remove('loggedInUser');
  }
}




}



