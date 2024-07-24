import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PinListComponent } from './components/pin-list/pin-list.component';
import { PinDetailComponent } from './components/pin-detail/pin-detail.component';
import { SentimentAnalysisComponent } from './components/sentiment-analysis/sentiment-analysis.component';
import { ModelFeedbackComponent } from './components/model-feedback/model-feedback.component';

const routes: Routes = [
  { path: '', redirectTo: '/pins', pathMatch: 'full' },
  { path: 'pins', component: PinListComponent },
  { path: 'pins/:id', component: PinDetailComponent },
  { path: 'analysis', component: SentimentAnalysisComponent },
  { path: 'feedback/:id', component: ModelFeedbackComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
