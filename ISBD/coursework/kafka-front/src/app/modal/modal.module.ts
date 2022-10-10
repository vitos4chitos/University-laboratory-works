import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {AddDocumentComponent } from './add-document/add-document.component';
import {DropdownModule } from 'primeng/dropdown'
import {InputTextModule} from "primeng/inputtext";
import {ButtonModule} from "primeng/button";
import {RippleModule} from "primeng/ripple";
import {FormsModule} from "@angular/forms";
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { MedCertifComponent } from './med-certif/med-certif.component'

@NgModule({
  declarations: [
    AddDocumentComponent,
    MedCertifComponent
  ],
  imports: [
    CommonModule,
    DropdownModule,
    InputTextModule,
    ButtonModule,
    RippleModule,
    FormsModule,
    BrowserAnimationsModule
  ],
  exports: [
    AddDocumentComponent,
    MedCertifComponent
  ]
})
export class ModalModule { }
