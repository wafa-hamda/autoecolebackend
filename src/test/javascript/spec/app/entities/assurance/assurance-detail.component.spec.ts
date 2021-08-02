import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AutoEcoleTestModule } from '../../../test.module';
import { AssuranceDetailComponent } from 'app/entities/assurance/assurance-detail.component';
import { Assurance } from 'app/shared/model/assurance.model';

describe('Component Tests', () => {
  describe('Assurance Management Detail Component', () => {
    let comp: AssuranceDetailComponent;
    let fixture: ComponentFixture<AssuranceDetailComponent>;
    const route = ({ data: of({ assurance: new Assurance(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AutoEcoleTestModule],
        declarations: [AssuranceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AssuranceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AssuranceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load assurance on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.assurance).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
