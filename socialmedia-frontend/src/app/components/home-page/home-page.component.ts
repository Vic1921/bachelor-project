import {Component, OnInit} from '@angular/core';
import {ButtonModule} from "primeng/button";

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  standalone: true,
  imports: [ButtonModule],
  styleUrl: './home-page.component.css'
})
export class HomePageComponent implements OnInit {
  constructor() { }

  ngOnInit(): void {
    console.log('init home')
  }

}
