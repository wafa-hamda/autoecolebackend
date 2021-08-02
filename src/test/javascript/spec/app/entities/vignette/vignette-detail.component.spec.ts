import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AutoEcoleTestModule } from '../../../test.module';
import { VignetteDetailComponent } from 'app/entities/vignette/vignette-detail.component';
import { Vignette } from 'app/shared/model/vignette.model';

describe('Component Tests', () => {
  describe('Vignette Management Detail Component', () => {
    let comp: VignetteDetailComponent;
    let fixture: ComponentFixture<VignetteDetailComponent>;
    const route = ({ data: of({ vignette: new Vignette(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AutoEcoleTestModule],
        declarations: [VignetteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(VignetteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VignetteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load vignette on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.vignette).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
