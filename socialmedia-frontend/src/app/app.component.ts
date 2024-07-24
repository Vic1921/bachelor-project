import { Component } from '@angular/core';
import {AppRoutingModule} from "./app-routing.module";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [AppRoutingModule],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'] // Correct the property name to 'styleUrls'
})
export class AppComponent {
  title = 'socialmedia-frontend';
}
