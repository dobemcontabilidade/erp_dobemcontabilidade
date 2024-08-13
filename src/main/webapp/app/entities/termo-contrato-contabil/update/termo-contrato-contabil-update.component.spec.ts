import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IPlanoContabil } from 'app/entities/plano-contabil/plano-contabil.model';
import { PlanoContabilService } from 'app/entities/plano-contabil/service/plano-contabil.service';
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
  let planoContabilService: PlanoContabilService;

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
    planoContabilService = TestBed.inject(PlanoContabilService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call PlanoContabil query and add missing value', () => {
      const termoContratoContabil: ITermoContratoContabil = { id: 456 };
      const planoContabil: IPlanoContabil = { id: 10889 };
      termoContratoContabil.planoContabil = planoContabil;

      const planoContabilCollection: IPlanoContabil[] = [{ id: 5070 }];
      jest.spyOn(planoContabilService, 'query').mockReturnValue(of(new HttpResponse({ body: planoContabilCollection })));
      const additionalPlanoContabils = [planoContabil];
      const expectedCollection: IPlanoContabil[] = [...additionalPlanoContabils, ...planoContabilCollection];
      jest.spyOn(planoContabilService, 'addPlanoContabilToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ termoContratoContabil });
      comp.ngOnInit();

      expect(planoContabilService.query).toHaveBeenCalled();
      expect(planoContabilService.addPlanoContabilToCollectionIfMissing).toHaveBeenCalledWith(
        planoContabilCollection,
        ...additionalPlanoContabils.map(expect.objectContaining),
      );
      expect(comp.planoContabilsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const termoContratoContabil: ITermoContratoContabil = { id: 456 };
      const planoContabil: IPlanoContabil = { id: 10293 };
      termoContratoContabil.planoContabil = planoContabil;

      activatedRoute.data = of({ termoContratoContabil });
      comp.ngOnInit();

      expect(comp.planoContabilsSharedCollection).toContain(planoContabil);
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

  describe('Compare relationships', () => {
    describe('comparePlanoContabil', () => {
      it('Should forward to planoContabilService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(planoContabilService, 'comparePlanoContabil');
        comp.comparePlanoContabil(entity, entity2);
        expect(planoContabilService.comparePlanoContabil).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
