import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AutoEcoleTestModule } from '../../../test.module';
import { EntretienUpdateComponent } from 'app/entities/entretien/entretien-update.component';
import { EntretienService } from 'app/entities/entretien/entretien.service';
import { Entretien } from 'app/shared/model/entretien.model';

describe('Component Tests', () => {
  describe('Entretien Management Update Component', () => {
    let comp: EntretienUpdateComponent;
    let fixture: ComponentFixture<EntretienUpdateComponent>;
    let service: EntretienService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AutoEcoleTestModule],
        declarations: [EntretienUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EntretienUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EntretienUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EntretienService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Entretien(123);
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
        const entity = new Entretien();
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
