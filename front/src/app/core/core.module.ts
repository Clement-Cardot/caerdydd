import * as fr from '@angular/common/locales/fr';

import { CommonModule, registerLocaleData } from '@angular/common';

import { LOCALE_ID, NgModule } from '@angular/core';

@NgModule({
  declarations: [
  ],
  imports: [
    CommonModule,
  ],
  exports: [
  ],
  providers: [
    { provide: LOCALE_ID, useValue: 'fr-FR' }
  ],
})

export class CoreModule {
  constructor() {
    registerLocaleData(fr.default);
  }
}
