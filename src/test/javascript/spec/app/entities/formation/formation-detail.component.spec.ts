import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AutoEcoleTestModule } from '../../../test.module';
import { FormationDetailComponent } from 'app/entities/formation/formation-detail.component';
import { Formation } from 'app/shared/model/formation.model';

describe('Component Tests', () => {
  describe('Formation Management Detail Component', () => {
    let comp: FormationDetailComponent;
    let fixture: ComponentFixture<FormationDetailComponent>;
    const route = ({ data: of({ formation: new Formation(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AutoEcoleTestModule],
        declarations: [FormationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FormationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FormationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load formation on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.formation).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
