import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IContador } from 'app/entities/contador/contador.model';
import { ContadorService } from 'app/entities/contador/service/contador.service';
import { IAreaContabil } from 'app/entities/area-contabil/area-contabil.model';
import { AreaContabilService } from 'app/entities/area-contabil/service/area-contabil.service';
import { IAreaContabilContador } from '../area-contabil-contador.model';
import { AreaContabilContadorService } from '../service/area-contabil-contador.service';
import { AreaContabilContadorFormService } from './area-contabil-contador-form.service';

import { AreaContabilContadorUpdateComponent } from './area-contabil-contador-update.component';

describe('AreaContabilContador Management Update Component', () => {
  let comp: AreaContabilContadorUpdateComponent;
  let fixture: ComponentFixture<AreaContabilContadorUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let areaContabilContadorFormService: AreaContabilContadorFormService;
  let areaContabilContadorService: AreaContabilContadorService;
  let contadorService: ContadorService;
  let areaContabilService: AreaContabilService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AreaContabilContadorUpdateComponent],
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
      .overrideTemplate(AreaContabilContadorUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AreaContabilContadorUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    areaContabilContadorFormService = TestBed.inject(AreaContabilContadorFormService);
    areaContabilContadorService = TestBed.inject(AreaContabilContadorService);
    contadorService = TestBed.inject(ContadorService);
    areaContabilService = TestBed.inject(AreaContabilService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Contador query and add missing value', () => {
      const areaContabilContador: IAreaContabilContador = { id: 456 };
      const contador: IContador = { id: 1336 };
      areaContabilContador.contador = contador;

      const contadorCollection: IContador[] = [{ id: 31901 }];
      jest.spyOn(contadorService, 'query').mockReturnValue(of(new HttpResponse({ body: contadorCollection })));
      const additionalContadors = [contador];
      const expectedCollection: IContador[] = [...additionalContadors, ...contadorCollection];
      jest.spyOn(contadorService, 'addContadorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ areaContabilContador });
      comp.ngOnInit();

      expect(contadorService.query).toHaveBeenCalled();
      expect(contadorService.addContadorToCollectionIfMissing).toHaveBeenCalledWith(
        contadorCollection,
        ...additionalContadors.map(expect.objectContaining),
      );
      expect(comp.contadorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call AreaContabil query and add missing value', () => {
      const areaContabilContador: IAreaContabilContador = { id: 456 };
      const areaContabil: IAreaContabil = { id: 1339 };
      areaContabilContador.areaContabil = areaContabil;

      const areaContabilCollection: IAreaContabil[] = [{ id: 6998 }];
      jest.spyOn(areaContabilService, 'query').mockReturnValue(of(new HttpResponse({ body: areaContabilCollection })));
      const additionalAreaContabils = [areaContabil];
      const expectedCollection: IAreaContabil[] = [...additionalAreaContabils, ...areaContabilCollection];
      jest.spyOn(areaContabilService, 'addAreaContabilToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ areaContabilContador });
      comp.ngOnInit();

      expect(areaContabilService.query).toHaveBeenCalled();
      expect(areaContabilService.addAreaContabilToCollectionIfMissing).toHaveBeenCalledWith(
        areaContabilCollection,
        ...additionalAreaContabils.map(expect.objectContaining),
      );
      expect(comp.areaContabilsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const areaContabilContador: IAreaContabilContador = { id: 456 };
      const contador: IContador = { id: 3569 };
      areaContabilContador.contador = contador;
      const areaContabil: IAreaContabil = { id: 2361 };
      areaContabilContador.areaContabil = areaContabil;

      activatedRoute.data = of({ areaContabilContador });
      comp.ngOnInit();

      expect(comp.contadorsSharedCollection).toContain(contador);
      expect(comp.areaContabilsSharedCollection).toContain(areaContabil);
      expect(comp.areaContabilContador).toEqual(areaContabilContador);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAreaContabilContador>>();
      const areaContabilContador = { id: 123 };
      jest.spyOn(areaContabilContadorFormService, 'getAreaContabilContador').mockReturnValue(areaContabilContador);
      jest.spyOn(areaContabilContadorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ areaContabilContador });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: areaContabilContador }));
      saveSubject.complete();

      // THEN
      expect(areaContabilContadorFormService.getAreaContabilContador).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(areaContabilContadorService.update).toHaveBeenCalledWith(expect.objectContaining(areaContabilContador));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAreaContabilContador>>();
      const areaContabilContador = { id: 123 };
      jest.spyOn(areaContabilContadorFormService, 'getAreaContabilContador').mockReturnValue({ id: null });
      jest.spyOn(areaContabilContadorService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ areaContabilContador: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: areaContabilContador }));
      saveSubject.complete();

      // THEN
      expect(areaContabilContadorFormService.getAreaContabilContador).toHaveBeenCalled();
      expect(areaContabilContadorService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAreaContabilContador>>();
      const areaContabilContador = { id: 123 };
      jest.spyOn(areaContabilContadorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ areaContabilContador });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(areaContabilContadorService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareContador', () => {
      it('Should forward to contadorService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(contadorService, 'compareContador');
        comp.compareContador(entity, entity2);
        expect(contadorService.compareContador).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareAreaContabil', () => {
      it('Should forward to areaContabilService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(areaContabilService, 'compareAreaContabil');
        comp.compareAreaContabil(entity, entity2);
        expect(areaContabilService.compareAreaContabil).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
