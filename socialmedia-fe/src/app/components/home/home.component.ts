import { Component } from '@angular/core';
import {RouterLink, RouterOutlet} from '@angular/router';
import {ButtonModule} from "primeng/button";

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [RouterOutlet, RouterLink, ButtonModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {

}
