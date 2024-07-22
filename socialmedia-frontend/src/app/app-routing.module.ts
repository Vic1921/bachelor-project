import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PinListComponent } from './pin-list/pin-list.component';
import { PinDetailComponent } from './pin-detail/pin-detail.component';
import { SentimentAnalysisComponent } from './sentiment-analysis/sentiment-analysis.component';
import { ModelFeedbackComponent } from './model-feedback/model-feedback.component';

const routes: Routes = [
  { path: '', redirectTo: '/pins', pathMatch: 'full' },
  { path: 'pins', component: PinListComponent },
  { path: 'pins/:id', component: PinDetailComponent },
  { path: 'analysis/:id', component: SentimentAnalysisComponent },
  { path: 'feedback/:id', component: ModelFeedbackComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }