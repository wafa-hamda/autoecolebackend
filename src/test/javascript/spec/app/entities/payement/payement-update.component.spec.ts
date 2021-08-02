import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AutoEcoleTestModule } from '../../../test.module';
import { PayementUpdateComponent } from 'app/entities/payement/payement-update.component';
import { PayementService } from 'app/entities/payement/payement.service';
import { Payement } from 'app/shared/model/payement.model';

describe('Component Tests', () => {
  describe('Payement Management Update Component', () => {
    let comp: PayementUpdateComponent;
    let fixture: ComponentFixture<PayementUpdateComponent>;
    let service: PayementService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AutoEcoleTestModule],
        declarations: [PayementUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PayementUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PayementUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PayementService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Payement(123);
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
        const entity = new Payement();
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
