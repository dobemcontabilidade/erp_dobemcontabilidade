import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { TermoContratoContabilService } from '../service/termo-contrato-contabil.service';
import { ITermoContratoContabil } from '../termo-contrato-contabil.model';
import { TermoContratoContabilFormService } from './termo-contrato-contabil-form.service';

import { TermoContratoContabilUpdateComponent } from './termo-contrato-contabil-update.component';

describe('TermoContratoContabil Management Update Component', () => {
  let comp: TermoContratoContabilUpdateComponent;
  let fixture: ComponentFixture<TermoContratoContabilUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let termoContratoContabilFormService: TermoContratoContabilFormService;
  let termoContratoContabilService: TermoContratoContabilService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TermoContratoContabilUpdateComponent],
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
      .overrideTemplate(TermoContratoContabilUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TermoContratoContabilUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    termoContratoContabilFormService = TestBed.inject(TermoContratoContabilFormService);
    termoContratoContabilService = TestBed.inject(TermoContratoContabilService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const termoContratoContabil: ITermoContratoContabil = { id: 456 };

      activatedRoute.data = of({ termoContratoContabil });
      comp.ngOnInit();

      expect(comp.termoContratoContabil).toEqual(termoContratoContabil);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITermoContratoContabil>>();
      const termoContratoContabil = { id: 123 };
      jest.spyOn(termoContratoContabilFormService, 'getTermoContratoContabil').mockReturnValue(termoContratoContabil);
      jest.spyOn(termoContratoContabilService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ termoContratoContabil });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: termoContratoContabil }));
      saveSubject.complete();

      // THEN
      expect(termoContratoContabilFormService.getTermoContratoContabil).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(termoContratoContabilService.update).toHaveBeenCalledWith(expect.objectContaining(termoContratoContabil));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITermoContratoContabil>>();
      const termoContratoContabil = { id: 123 };
      jest.spyOn(termoContratoContabilFormService, 'getTermoContratoContabil').mockReturnValue({ id: null });
      jest.spyOn(termoContratoContabilService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ termoContratoContabil: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: termoContratoContabil }));
      saveSubject.complete();

      // THEN
      expect(termoContratoContabilFormService.getTermoContratoContabil).toHaveBeenCalled();
      expect(termoContratoContabilService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITermoContratoContabil>>();
      const termoContratoContabil = { id: 123 };
      jest.spyOn(termoContratoContabilService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ termoContratoContabil });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(termoContratoContabilService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
