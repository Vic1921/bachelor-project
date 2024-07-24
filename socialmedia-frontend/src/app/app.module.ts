import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from "./app.component";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { SentimentAnalysisComponent } from "./components/sentiment-analysis/sentiment-analysis.component";
import { PinListComponent } from "./components/pin-list/pin-list.component";
import { HomePageComponent } from "./components/home-page/home-page.component";

@NgModule({
  declarations: [
    PinListComponent,

  ],
  imports: [
    AppComponent,
    BrowserModule,
    BrowserAnimationsModule,
    HomePageComponent,
    SentimentAnalysisComponent
  ],
  providers: [],
  bootstrap: []
})
export class AppModule { }
