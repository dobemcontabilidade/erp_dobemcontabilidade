import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IAreaContabil } from 'app/entities/area-contabil/area-contabil.model';
import { AreaContabilService } from 'app/entities/area-contabil/service/area-contabil.service';
import { IPerfilContador } from 'app/entities/perfil-contador/perfil-contador.model';
import { PerfilContadorService } from 'app/entities/perfil-contador/service/perfil-contador.service';
import { IPerfilContadorAreaContabil } from '../perfil-contador-area-contabil.model';
import { PerfilContadorAreaContabilService } from '../service/perfil-contador-area-contabil.service';
import { PerfilContadorAreaContabilFormService } from './perfil-contador-area-contabil-form.service';

import { PerfilContadorAreaContabilUpdateComponent } from './perfil-contador-area-contabil-update.component';

describe('PerfilContadorAreaContabil Management Update Component', () => {
  let comp: PerfilContadorAreaContabilUpdateComponent;
  let fixture: ComponentFixture<PerfilContadorAreaContabilUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let perfilContadorAreaContabilFormService: PerfilContadorAreaContabilFormService;
  let perfilContadorAreaContabilService: PerfilContadorAreaContabilService;
  let areaContabilService: AreaContabilService;
  let perfilContadorService: PerfilContadorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [PerfilContadorAreaContabilUpdateComponent],
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
      .overrideTemplate(PerfilContadorAreaContabilUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PerfilContadorAreaContabilUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    perfilContadorAreaContabilFormService = TestBed.inject(PerfilContadorAreaContabilFormService);
    perfilContadorAreaContabilService = TestBed.inject(PerfilContadorAreaContabilService);
    areaContabilService = TestBed.inject(AreaContabilService);
    perfilContadorService = TestBed.inject(PerfilContadorService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call AreaContabil query and add missing value', () => {
      const perfilContadorAreaContabil: IPerfilContadorAreaContabil = { id: 456 };
      const areaContabil: IAreaContabil = { id: 7812 };
      perfilContadorAreaContabil.areaContabil = areaContabil;

      const areaContabilCollection: IAreaContabil[] = [{ id: 18854 }];
      jest.spyOn(areaContabilService, 'query').mockReturnValue(of(new HttpResponse({ body: areaContabilCollection })));
      const additionalAreaContabils = [areaContabil];
      const expectedCollection: IAreaContabil[] = [...additionalAreaContabils, ...areaContabilCollection];
      jest.spyOn(areaContabilService, 'addAreaContabilToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ perfilContadorAreaContabil });
      comp.ngOnInit();

      expect(areaContabilService.query).toHaveBeenCalled();
      expect(areaContabilService.addAreaContabilToCollectionIfMissing).toHaveBeenCalledWith(
        areaContabilCollection,
        ...additionalAreaContabils.map(expect.objectContaining),
      );
      expect(comp.areaContabilsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call PerfilContador query and add missing value', () => {
      const perfilContadorAreaContabil: IPerfilContadorAreaContabil = { id: 456 };
      const perfilContador: IPerfilContador = { id: 28492 };
      perfilContadorAreaContabil.perfilContador = perfilContador;

      const perfilContadorCollection: IPerfilContador[] = [{ id: 18740 }];
      jest.spyOn(perfilContadorService, 'query').mockReturnValue(of(new HttpResponse({ body: perfilContadorCollection })));
      const additionalPerfilContadors = [perfilContador];
      const expectedCollection: IPerfilContador[] = [...additionalPerfilContadors, ...perfilContadorCollection];
      jest.spyOn(perfilContadorService, 'addPerfilContadorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ perfilContadorAreaContabil });
      comp.ngOnInit();

      expect(perfilContadorService.query).toHaveBeenCalled();
      expect(perfilContadorService.addPerfilContadorToCollectionIfMissing).toHaveBeenCalledWith(
        perfilContadorCollection,
        ...additionalPerfilContadors.map(expect.objectContaining),
      );
      expect(comp.perfilContadorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const perfilContadorAreaContabil: IPerfilContadorAreaContabil = { id: 456 };
      const areaContabil: IAreaContabil = { id: 21997 };
      perfilContadorAreaContabil.areaContabil = areaContabil;
      const perfilContador: IPerfilContador = { id: 18318 };
      perfilContadorAreaContabil.perfilContador = perfilContador;

      activatedRoute.data = of({ perfilContadorAreaContabil });
      comp.ngOnInit();

      expect(comp.areaContabilsSharedCollection).toContain(areaContabil);
      expect(comp.perfilContadorsSharedCollection).toContain(perfilContador);
      expect(comp.perfilContadorAreaContabil).toEqual(perfilContadorAreaContabil);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPerfilContadorAreaContabil>>();
      const perfilContadorAreaContabil = { id: 123 };
      jest.spyOn(perfilContadorAreaContabilFormService, 'getPerfilContadorAreaContabil').mockReturnValue(perfilContadorAreaContabil);
      jest.spyOn(perfilContadorAreaContabilService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ perfilContadorAreaContabil });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: perfilContadorAreaContabil }));
      saveSubject.complete();

      // THEN
      expect(perfilContadorAreaContabilFormService.getPerfilContadorAreaContabil).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(perfilContadorAreaContabilService.update).toHaveBeenCalledWith(expect.objectContaining(perfilContadorAreaContabil));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPerfilContadorAreaContabil>>();
      const perfilContadorAreaContabil = { id: 123 };
      jest.spyOn(perfilContadorAreaContabilFormService, 'getPerfilContadorAreaContabil').mockReturnValue({ id: null });
      jest.spyOn(perfilContadorAreaContabilService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ perfilContadorAreaContabil: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: perfilContadorAreaContabil }));
      saveSubject.complete();

      // THEN
      expect(perfilContadorAreaContabilFormService.getPerfilContadorAreaContabil).toHaveBeenCalled();
      expect(perfilContadorAreaContabilService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPerfilContadorAreaContabil>>();
      const perfilContadorAreaContabil = { id: 123 };
      jest.spyOn(perfilContadorAreaContabilService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ perfilContadorAreaContabil });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(perfilContadorAreaContabilService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareAreaContabil', () => {
      it('Should forward to areaContabilService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(areaContabilService, 'compareAreaContabil');
        comp.compareAreaContabil(entity, entity2);
        expect(areaContabilService.compareAreaContabil).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('comparePerfilContador', () => {
      it('Should forward to perfilContadorService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(perfilContadorService, 'comparePerfilContador');
        comp.comparePerfilContador(entity, entity2);
        expect(perfilContadorService.comparePerfilContador).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
