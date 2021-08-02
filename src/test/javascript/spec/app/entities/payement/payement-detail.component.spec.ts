import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AutoEcoleTestModule } from '../../../test.module';
import { PayementDetailComponent } from 'app/entities/payement/payement-detail.component';
import { Payement } from 'app/shared/model/payement.model';

describe('Component Tests', () => {
  describe('Payement Management Detail Component', () => {
    let comp: PayementDetailComponent;
    let fixture: ComponentFixture<PayementDetailComponent>;
    const route = ({ data: of({ payement: new Payement(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AutoEcoleTestModule],
        declarations: [PayementDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PayementDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PayementDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load payement on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.payement).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
