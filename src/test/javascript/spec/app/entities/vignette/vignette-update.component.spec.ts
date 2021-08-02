import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AutoEcoleTestModule } from '../../../test.module';
import { VignetteUpdateComponent } from 'app/entities/vignette/vignette-update.component';
import { VignetteService } from 'app/entities/vignette/vignette.service';
import { Vignette } from 'app/shared/model/vignette.model';

describe('Component Tests', () => {
  describe('Vignette Management Update Component', () => {
    let comp: VignetteUpdateComponent;
    let fixture: ComponentFixture<VignetteUpdateComponent>;
    let service: VignetteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AutoEcoleTestModule],
        declarations: [VignetteUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(VignetteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VignetteUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VignetteService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Vignette(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Vignette();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
