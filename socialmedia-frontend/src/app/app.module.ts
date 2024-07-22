import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module'; // Ensure this is correctly imported
import { RouterModule } from '@angular/router'; // Import RouterModule here

@NgModule({
  declarations: [
    // other components
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    RouterModule, // Add RouterModule to the imports array
    AppComponent // Import AppComponent here
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
