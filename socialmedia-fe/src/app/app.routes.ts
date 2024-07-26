import { Routes } from '@angular/router';
import {HomeComponent} from "./home/home.component";
import {PinsComponent} from "./pins/pins.component";

export const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    title: 'Home'
  },
  {
    path: 'pins',
    component: PinsComponent,
    title: 'Home'
  }
];
