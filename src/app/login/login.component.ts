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


constructor( private router: Router, private http: HttpClient) { }
  

ngOnInit(): void {

  this.onSubmit(this.username, this.password);
  
 }


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



