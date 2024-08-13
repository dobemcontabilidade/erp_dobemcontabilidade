import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { PlanoContabilService } from '../service/plano-contabil.service';
import { IPlanoContabil } from '../plano-contabil.model';
import { PlanoContabilFormService } from './plano-contabil-form.service';

import { PlanoContabilUpdateComponent } from './plano-contabil-update.component';

describe('PlanoContabil Management Update Component', () => {
  let comp: PlanoContabilUpdateComponent;
  let fixture: ComponentFixture<PlanoContabilUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let planoContabilFormService: PlanoContabilFormService;
  let planoContabilService: PlanoContabilService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [PlanoContabilUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(PlanoContabilUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PlanoContabilUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    planoContabilFormService = TestBed.inject(PlanoContabilFormService);
    planoContabilService = TestBed.inject(PlanoContabilService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const planoContabil: IPlanoContabil = { id: 456 };

      activatedRoute.data = of({ planoContabil });
      comp.ngOnInit();

      expect(comp.planoContabil).toEqual(planoContabil);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPlanoContabil>>();
      const planoContabil = { id: 123 };
      jest.spyOn(planoContabilFormService, 'getPlanoContabil').mockReturnValue(planoContabil);
      jest.spyOn(planoContabilService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ planoContabil });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: planoContabil }));
      saveSubject.complete();

      // THEN
      expect(planoContabilFormService.getPlanoContabil).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(planoContabilService.update).toHaveBeenCalledWith(expect.objectContaining(planoContabil));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPlanoContabil>>();
      const planoContabil = { id: 123 };
      jest.spyOn(planoContabilFormService, 'getPlanoContabil').mockReturnValue({ id: null });
      jest.spyOn(planoContabilService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ planoContabil: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: planoContabil }));
      saveSubject.complete();

      // THEN
      expect(planoContabilFormService.getPlanoContabil).toHaveBeenCalled();
      expect(planoContabilService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPlanoContabil>>();
      const planoContabil = { id: 123 };
      jest.spyOn(planoContabilService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ planoContabil });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(planoContabilService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
