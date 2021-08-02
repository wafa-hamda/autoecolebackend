import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AutoEcoleTestModule } from '../../../test.module';
import { EntretienDetailComponent } from 'app/entities/entretien/entretien-detail.component';
import { Entretien } from 'app/shared/model/entretien.model';

describe('Component Tests', () => {
  describe('Entretien Management Detail Component', () => {
    let comp: EntretienDetailComponent;
    let fixture: ComponentFixture<EntretienDetailComponent>;
    const route = ({ data: of({ entretien: new Entretien(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AutoEcoleTestModule],
        declarations: [EntretienDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EntretienDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EntretienDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load entretien on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.entretien).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
