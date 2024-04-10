import { Component } from '@angular/core';
import { HeaderComponent } from "../header/header.component";

@Component({
    selector: 'app-administ',
    standalone: true,
    templateUrl: './administ.component.html',
    styleUrl: './administ.component.scss',
    imports: [HeaderComponent]
})
export class AdministComponent {

}
