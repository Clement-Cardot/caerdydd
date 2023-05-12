import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NotationComponent } from './component/notation/notation.component';

import { MaterialModule } from '../material.module';
import { LeaderMarkComponent } from './component/leader-mark/leader-mark.component';
import { FormsModule } from '@angular/forms';
import { JuryMemberMarkComponent } from './component/jury-member-mark/jury-member-mark.component';

@NgModule({
    declarations: [
        NotationComponent,
        LeaderMarkComponent,
        JuryMemberMarkComponent,
    ],
    exports: [
        NotationComponent,
    ],
    imports: [
        MaterialModule,
        CommonModule,
        FormsModule,
    ]
})
export class NotationModule { }
