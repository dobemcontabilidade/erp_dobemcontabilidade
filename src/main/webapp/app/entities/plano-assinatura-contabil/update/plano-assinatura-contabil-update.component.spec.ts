import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { PlanoAssinaturaContabilService } from '../service/plano-assinatura-contabil.service';
import { IPlanoAssinaturaContabil } from '../plano-assinatura-contabil.model';
import { PlanoAssinaturaContabilFormService } from './plano-assinatura-contabil-form.service';

import { PlanoAssinaturaContabilUpdateComponent } from './plano-assinatura-contabil-update.component';

describe('PlanoAssinaturaContabil Management Update Component', () => {
  let comp: PlanoAssinaturaContabilUpdateComponent;
  let fixture: ComponentFixture<PlanoAssinaturaContabilUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let planoAssinaturaContabilFormService: PlanoAssinaturaContabilFormService;
  let planoAssinaturaContabilService: PlanoAssinaturaContabilService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [PlanoAssinaturaContabilUpdateComponent],
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
      .overrideTemplate(PlanoAssinaturaContabilUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PlanoAssinaturaContabilUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    planoAssinaturaContabilFormService = TestBed.inject(PlanoAssinaturaContabilFormService);
    planoAssinaturaContabilService = TestBed.inject(PlanoAssinaturaContabilService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const planoAssinaturaContabil: IPlanoAssinaturaContabil = { id: 456 };

      activatedRoute.data = of({ planoAssinaturaContabil });
      comp.ngOnInit();

      expect(comp.planoAssinaturaContabil).toEqual(planoAssinaturaContabil);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPlanoAssinaturaContabil>>();
      const planoAssinaturaContabil = { id: 123 };
      jest.spyOn(planoAssinaturaContabilFormService, 'getPlanoAssinaturaContabil').mockReturnValue(planoAssinaturaContabil);
      jest.spyOn(planoAssinaturaContabilService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ planoAssinaturaContabil });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: planoAssinaturaContabil }));
      saveSubject.complete();

      // THEN
      expect(planoAssinaturaContabilFormService.getPlanoAssinaturaContabil).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(planoAssinaturaContabilService.update).toHaveBeenCalledWith(expect.objectContaining(planoAssinaturaContabil));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPlanoAssinaturaContabil>>();
      const planoAssinaturaContabil = { id: 123 };
      jest.spyOn(planoAssinaturaContabilFormService, 'getPlanoAssinaturaContabil').mockReturnValue({ id: null });
      jest.spyOn(planoAssinaturaContabilService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ planoAssinaturaContabil: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: planoAssinaturaContabil }));
      saveSubject.complete();

      // THEN
      expect(planoAssinaturaContabilFormService.getPlanoAssinaturaContabil).toHaveBeenCalled();
      expect(planoAssinaturaContabilService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPlanoAssinaturaContabil>>();
      const planoAssinaturaContabil = { id: 123 };
      jest.spyOn(planoAssinaturaContabilService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ planoAssinaturaContabil });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(planoAssinaturaContabilService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
