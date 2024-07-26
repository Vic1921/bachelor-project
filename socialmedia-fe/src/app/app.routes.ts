import { Routes } from '@angular/router';
import { HomeComponent} from "./components/home/home.component";
import {PinsComponent} from "./components/pins/pins.component";
import {BoardsComponent} from "./components/boards/boards.component";
import {BoardDetailComponent} from "./components/board-detail/board-detail.component";

export const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    title: 'Home'
  },
  {
    path: 'pins',
    component: PinsComponent,
    title: 'Pins Overview'
  },
  {
    path: 'boards',
    component: BoardsComponent,
    title: 'Boards Overview'
  },
  {
    path: 'boards/:boardId',
    component: BoardDetailComponent,
    title: 'Board Detail'
  }
];
