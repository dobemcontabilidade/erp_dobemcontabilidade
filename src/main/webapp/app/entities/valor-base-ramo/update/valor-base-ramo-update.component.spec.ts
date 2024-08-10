import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IRamo } from 'app/entities/ramo/ramo.model';
import { RamoService } from 'app/entities/ramo/service/ramo.service';
import { IPlanoContabil } from 'app/entities/plano-contabil/plano-contabil.model';
import { PlanoContabilService } from 'app/entities/plano-contabil/service/plano-contabil.service';
import { IValorBaseRamo } from '../valor-base-ramo.model';
import { ValorBaseRamoService } from '../service/valor-base-ramo.service';
import { ValorBaseRamoFormService } from './valor-base-ramo-form.service';

import { ValorBaseRamoUpdateComponent } from './valor-base-ramo-update.component';

describe('ValorBaseRamo Management Update Component', () => {
  let comp: ValorBaseRamoUpdateComponent;
  let fixture: ComponentFixture<ValorBaseRamoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let valorBaseRamoFormService: ValorBaseRamoFormService;
  let valorBaseRamoService: ValorBaseRamoService;
  let ramoService: RamoService;
  let planoContabilService: PlanoContabilService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ValorBaseRamoUpdateComponent],
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
      .overrideTemplate(ValorBaseRamoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ValorBaseRamoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    valorBaseRamoFormService = TestBed.inject(ValorBaseRamoFormService);
    valorBaseRamoService = TestBed.inject(ValorBaseRamoService);
    ramoService = TestBed.inject(RamoService);
    planoContabilService = TestBed.inject(PlanoContabilService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Ramo query and add missing value', () => {
      const valorBaseRamo: IValorBaseRamo = { id: 456 };
      const ramo: IRamo = { id: 5676 };
      valorBaseRamo.ramo = ramo;

      const ramoCollection: IRamo[] = [{ id: 1192 }];
      jest.spyOn(ramoService, 'query').mockReturnValue(of(new HttpResponse({ body: ramoCollection })));
      const additionalRamos = [ramo];
      const expectedCollection: IRamo[] = [...additionalRamos, ...ramoCollection];
      jest.spyOn(ramoService, 'addRamoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ valorBaseRamo });
      comp.ngOnInit();

      expect(ramoService.query).toHaveBeenCalled();
      expect(ramoService.addRamoToCollectionIfMissing).toHaveBeenCalledWith(
        ramoCollection,
        ...additionalRamos.map(expect.objectContaining),
      );
      expect(comp.ramosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call PlanoContabil query and add missing value', () => {
      const valorBaseRamo: IValorBaseRamo = { id: 456 };
      const planoContabil: IPlanoContabil = { id: 3615 };
      valorBaseRamo.planoContabil = planoContabil;

      const planoContabilCollection: IPlanoContabil[] = [{ id: 25976 }];
      jest.spyOn(planoContabilService, 'query').mockReturnValue(of(new HttpResponse({ body: planoContabilCollection })));
      const additionalPlanoContabils = [planoContabil];
      const expectedCollection: IPlanoContabil[] = [...additionalPlanoContabils, ...planoContabilCollection];
      jest.spyOn(planoContabilService, 'addPlanoContabilToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ valorBaseRamo });
      comp.ngOnInit();

      expect(planoContabilService.query).toHaveBeenCalled();
      expect(planoContabilService.addPlanoContabilToCollectionIfMissing).toHaveBeenCalledWith(
        planoContabilCollection,
        ...additionalPlanoContabils.map(expect.objectContaining),
      );
      expect(comp.planoContabilsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const valorBaseRamo: IValorBaseRamo = { id: 456 };
      const ramo: IRamo = { id: 32369 };
      valorBaseRamo.ramo = ramo;
      const planoContabil: IPlanoContabil = { id: 7737 };
      valorBaseRamo.planoContabil = planoContabil;

      activatedRoute.data = of({ valorBaseRamo });
      comp.ngOnInit();

      expect(comp.ramosSharedCollection).toContain(ramo);
      expect(comp.planoContabilsSharedCollection).toContain(planoContabil);
      expect(comp.valorBaseRamo).toEqual(valorBaseRamo);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IValorBaseRamo>>();
      const valorBaseRamo = { id: 123 };
      jest.spyOn(valorBaseRamoFormService, 'getValorBaseRamo').mockReturnValue(valorBaseRamo);
      jest.spyOn(valorBaseRamoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ valorBaseRamo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: valorBaseRamo }));
      saveSubject.complete();

      // THEN
      expect(valorBaseRamoFormService.getValorBaseRamo).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(valorBaseRamoService.update).toHaveBeenCalledWith(expect.objectContaining(valorBaseRamo));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IValorBaseRamo>>();
      const valorBaseRamo = { id: 123 };
      jest.spyOn(valorBaseRamoFormService, 'getValorBaseRamo').mockReturnValue({ id: null });
      jest.spyOn(valorBaseRamoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ valorBaseRamo: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: valorBaseRamo }));
      saveSubject.complete();

      // THEN
      expect(valorBaseRamoFormService.getValorBaseRamo).toHaveBeenCalled();
      expect(valorBaseRamoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IValorBaseRamo>>();
      const valorBaseRamo = { id: 123 };
      jest.spyOn(valorBaseRamoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ valorBaseRamo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(valorBaseRamoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareRamo', () => {
      it('Should forward to ramoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(ramoService, 'compareRamo');
        comp.compareRamo(entity, entity2);
        expect(ramoService.compareRamo).toHaveBeenCalledWith(entity, entity2);
      });
    });

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
