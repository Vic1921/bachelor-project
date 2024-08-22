import { Routes } from '@angular/router';
import { HomeComponent } from "./components/home/home.component";
import { PinsComponent } from "./components/pins/pins.component";
import { BoardsComponent } from "./components/boards/boards.component";
import { BoardDetailComponent } from "./components/board-detail/board-detail.component";
import {PinDetailComponent} from "./components/pin-detail/pin-detail.component";
import {PinAnalysisComponent} from "./components/pin-analysis/pin-analysis.component";
import {GlobalKPIComponent} from "./components/global-kpi/global-kpi.component";

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
    path: 'board/:boardId',
    component: BoardDetailComponent,
    title: 'Board Detail'
  },
  {
    path: 'pin/:pinId',
    component: PinDetailComponent,
    title: 'Pin Detail'
  },
  {
    path: 'pin-analysis/:pinId',
    component: PinAnalysisComponent,
    title: 'Analysis Dashboard'
  },
  {
    path: 'global-kpis',
    component: GlobalKPIComponent,
    title: 'Global KPI'
  }
];
