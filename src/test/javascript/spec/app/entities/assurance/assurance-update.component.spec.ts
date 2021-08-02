import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AutoEcoleTestModule } from '../../../test.module';
import { AssuranceUpdateComponent } from 'app/entities/assurance/assurance-update.component';
import { AssuranceService } from 'app/entities/assurance/assurance.service';
import { Assurance } from 'app/shared/model/assurance.model';

describe('Component Tests', () => {
  describe('Assurance Management Update Component', () => {
    let comp: AssuranceUpdateComponent;
    let fixture: ComponentFixture<AssuranceUpdateComponent>;
    let service: AssuranceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AutoEcoleTestModule],
        declarations: [AssuranceUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AssuranceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AssuranceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AssuranceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Assurance(123);
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
        const entity = new Assurance();
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
