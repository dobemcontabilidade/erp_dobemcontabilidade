import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IRamo } from 'app/entities/ramo/ramo.model';
import { RamoService } from 'app/entities/ramo/service/ramo.service';
import { IPlanoAssinaturaContabil } from 'app/entities/plano-assinatura-contabil/plano-assinatura-contabil.model';
import { PlanoAssinaturaContabilService } from 'app/entities/plano-assinatura-contabil/service/plano-assinatura-contabil.service';
import { IAdicionalRamo } from '../adicional-ramo.model';
import { AdicionalRamoService } from '../service/adicional-ramo.service';
import { AdicionalRamoFormService } from './adicional-ramo-form.service';

import { AdicionalRamoUpdateComponent } from './adicional-ramo-update.component';

describe('AdicionalRamo Management Update Component', () => {
  let comp: AdicionalRamoUpdateComponent;
  let fixture: ComponentFixture<AdicionalRamoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let adicionalRamoFormService: AdicionalRamoFormService;
  let adicionalRamoService: AdicionalRamoService;
  let ramoService: RamoService;
  let planoAssinaturaContabilService: PlanoAssinaturaContabilService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AdicionalRamoUpdateComponent],
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
      .overrideTemplate(AdicionalRamoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AdicionalRamoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    adicionalRamoFormService = TestBed.inject(AdicionalRamoFormService);
    adicionalRamoService = TestBed.inject(AdicionalRamoService);
    ramoService = TestBed.inject(RamoService);
    planoAssinaturaContabilService = TestBed.inject(PlanoAssinaturaContabilService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Ramo query and add missing value', () => {
      const adicionalRamo: IAdicionalRamo = { id: 456 };
      const ramo: IRamo = { id: 516 };
      adicionalRamo.ramo = ramo;

      const ramoCollection: IRamo[] = [{ id: 589 }];
      jest.spyOn(ramoService, 'query').mockReturnValue(of(new HttpResponse({ body: ramoCollection })));
      const additionalRamos = [ramo];
      const expectedCollection: IRamo[] = [...additionalRamos, ...ramoCollection];
      jest.spyOn(ramoService, 'addRamoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ adicionalRamo });
      comp.ngOnInit();

      expect(ramoService.query).toHaveBeenCalled();
      expect(ramoService.addRamoToCollectionIfMissing).toHaveBeenCalledWith(
        ramoCollection,
        ...additionalRamos.map(expect.objectContaining),
      );
      expect(comp.ramosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call PlanoAssinaturaContabil query and add missing value', () => {
      const adicionalRamo: IAdicionalRamo = { id: 456 };
      const planoAssinaturaContabil: IPlanoAssinaturaContabil = { id: 4071 };
      adicionalRamo.planoAssinaturaContabil = planoAssinaturaContabil;

      const planoAssinaturaContabilCollection: IPlanoAssinaturaContabil[] = [{ id: 4039 }];
      jest
        .spyOn(planoAssinaturaContabilService, 'query')
        .mockReturnValue(of(new HttpResponse({ body: planoAssinaturaContabilCollection })));
      const additionalPlanoAssinaturaContabils = [planoAssinaturaContabil];
      const expectedCollection: IPlanoAssinaturaContabil[] = [...additionalPlanoAssinaturaContabils, ...planoAssinaturaContabilCollection];
      jest.spyOn(planoAssinaturaContabilService, 'addPlanoAssinaturaContabilToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ adicionalRamo });
      comp.ngOnInit();

      expect(planoAssinaturaContabilService.query).toHaveBeenCalled();
      expect(planoAssinaturaContabilService.addPlanoAssinaturaContabilToCollectionIfMissing).toHaveBeenCalledWith(
        planoAssinaturaContabilCollection,
        ...additionalPlanoAssinaturaContabils.map(expect.objectContaining),
      );
      expect(comp.planoAssinaturaContabilsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const adicionalRamo: IAdicionalRamo = { id: 456 };
      const ramo: IRamo = { id: 8291 };
      adicionalRamo.ramo = ramo;
      const planoAssinaturaContabil: IPlanoAssinaturaContabil = { id: 18878 };
      adicionalRamo.planoAssinaturaContabil = planoAssinaturaContabil;

      activatedRoute.data = of({ adicionalRamo });
      comp.ngOnInit();

      expect(comp.ramosSharedCollection).toContain(ramo);
      expect(comp.planoAssinaturaContabilsSharedCollection).toContain(planoAssinaturaContabil);
      expect(comp.adicionalRamo).toEqual(adicionalRamo);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAdicionalRamo>>();
      const adicionalRamo = { id: 123 };
      jest.spyOn(adicionalRamoFormService, 'getAdicionalRamo').mockReturnValue(adicionalRamo);
      jest.spyOn(adicionalRamoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ adicionalRamo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: adicionalRamo }));
      saveSubject.complete();

      // THEN
      expect(adicionalRamoFormService.getAdicionalRamo).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(adicionalRamoService.update).toHaveBeenCalledWith(expect.objectContaining(adicionalRamo));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAdicionalRamo>>();
      const adicionalRamo = { id: 123 };
      jest.spyOn(adicionalRamoFormService, 'getAdicionalRamo').mockReturnValue({ id: null });
      jest.spyOn(adicionalRamoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ adicionalRamo: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: adicionalRamo }));
      saveSubject.complete();

      // THEN
      expect(adicionalRamoFormService.getAdicionalRamo).toHaveBeenCalled();
      expect(adicionalRamoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAdicionalRamo>>();
      const adicionalRamo = { id: 123 };
      jest.spyOn(adicionalRamoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ adicionalRamo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(adicionalRamoService.update).toHaveBeenCalled();
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

    describe('comparePlanoAssinaturaContabil', () => {
      it('Should forward to planoAssinaturaContabilService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(planoAssinaturaContabilService, 'comparePlanoAssinaturaContabil');
        comp.comparePlanoAssinaturaContabil(entity, entity2);
        expect(planoAssinaturaContabilService.comparePlanoAssinaturaContabil).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
