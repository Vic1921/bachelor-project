import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module'; // Ensure this is correctly imported
import { RouterModule } from '@angular/router';
import {AppComponent} from "./app.component";
import {SentimentAnalysisComponent} from "./components/sentiment-analysis/sentiment-analysis.component";
import {PinListComponent} from "./components/pin-list/pin-list.component";
@NgModule({
  declarations: [
    PinListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    RouterModule, // Add RouterModule to the imports array
    AppComponent, // Import AppComponent here
    SentimentAnalysisComponent,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
