import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AutoEcoleTestModule } from '../../../test.module';
import { SeanceUpdateComponent } from 'app/entities/seance/seance-update.component';
import { SeanceService } from 'app/entities/seance/seance.service';
import { Seance } from 'app/shared/model/seance.model';

describe('Component Tests', () => {
  describe('Seance Management Update Component', () => {
    let comp: SeanceUpdateComponent;
    let fixture: ComponentFixture<SeanceUpdateComponent>;
    let service: SeanceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AutoEcoleTestModule],
        declarations: [SeanceUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SeanceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SeanceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SeanceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Seance(123);
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
        const entity = new Seance();
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
