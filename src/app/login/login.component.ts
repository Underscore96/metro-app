import { Component } from '@angular/core';
import { FormControl, FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';

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
rememberMe = new FormControl(false)


constructor(private auth: AuthService, private router: Router, private http: HttpClient) { }
  

ngOnInit(): void {
  // this.auth.login(this.username, this.password)
  this.onSubmit(this.username, this.password);
  
 }

// login() {
//   if (this.username.trim().length === 0) {
//     this.errorMsg = "Username is required";
//   } else if (this.password.trim().length === 0) {
//     this.errorMsg = "Password is required";
//   } else {
//     this.errorMsg = "";
//     let res = this.auth.login(this.username, this.password);
//     if (res === 200) {
//       this.router.navigate(['admin']);
//     }
//     if (res === 403) {
//       this.errorMsg = "Invalid Credentials";
//     }
//   }
// }

 baseUrl = 'http://localhost:8080/Metro/rest/login';





onSubmit(username: string, password: string) {
  const url = `${this.baseUrl}?nomeUtente=${username}&password=${password}`;

 

  this.http.get<any[]>(url).subscribe(
    data => {
      if (data && data.length > 0) {
        console.log('Login successfulr.');
        
                this.router.navigate(['admin']);
               
          
      } else {
        
        console.log('Invalid username or password. Access denied.');
      }
    },
    error => {
      console.error('Errore nel login:', error)
      this.errorMsg = true;
      
    
    }
  );
}

}



