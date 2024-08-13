import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IAreaContabil } from 'app/entities/area-contabil/area-contabil.model';
import { AreaContabilService } from 'app/entities/area-contabil/service/area-contabil.service';
import { IEsfera } from 'app/entities/esfera/esfera.model';
import { EsferaService } from 'app/entities/esfera/service/esfera.service';
import { IServicoContabil } from '../servico-contabil.model';
import { ServicoContabilService } from '../service/servico-contabil.service';
import { ServicoContabilFormService } from './servico-contabil-form.service';

import { ServicoContabilUpdateComponent } from './servico-contabil-update.component';

describe('ServicoContabil Management Update Component', () => {
  let comp: ServicoContabilUpdateComponent;
  let fixture: ComponentFixture<ServicoContabilUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let servicoContabilFormService: ServicoContabilFormService;
  let servicoContabilService: ServicoContabilService;
  let areaContabilService: AreaContabilService;
  let esferaService: EsferaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ServicoContabilUpdateComponent],
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
      .overrideTemplate(ServicoContabilUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ServicoContabilUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    servicoContabilFormService = TestBed.inject(ServicoContabilFormService);
    servicoContabilService = TestBed.inject(ServicoContabilService);
    areaContabilService = TestBed.inject(AreaContabilService);
    esferaService = TestBed.inject(EsferaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call AreaContabil query and add missing value', () => {
      const servicoContabil: IServicoContabil = { id: 456 };
      const areaContabil: IAreaContabil = { id: 6167 };
      servicoContabil.areaContabil = areaContabil;

      const areaContabilCollection: IAreaContabil[] = [{ id: 31941 }];
      jest.spyOn(areaContabilService, 'query').mockReturnValue(of(new HttpResponse({ body: areaContabilCollection })));
      const additionalAreaContabils = [areaContabil];
      const expectedCollection: IAreaContabil[] = [...additionalAreaContabils, ...areaContabilCollection];
      jest.spyOn(areaContabilService, 'addAreaContabilToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ servicoContabil });
      comp.ngOnInit();

      expect(areaContabilService.query).toHaveBeenCalled();
      expect(areaContabilService.addAreaContabilToCollectionIfMissing).toHaveBeenCalledWith(
        areaContabilCollection,
        ...additionalAreaContabils.map(expect.objectContaining),
      );
      expect(comp.areaContabilsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Esfera query and add missing value', () => {
      const servicoContabil: IServicoContabil = { id: 456 };
      const esfera: IEsfera = { id: 28446 };
      servicoContabil.esfera = esfera;

      const esferaCollection: IEsfera[] = [{ id: 15821 }];
      jest.spyOn(esferaService, 'query').mockReturnValue(of(new HttpResponse({ body: esferaCollection })));
      const additionalEsferas = [esfera];
      const expectedCollection: IEsfera[] = [...additionalEsferas, ...esferaCollection];
      jest.spyOn(esferaService, 'addEsferaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ servicoContabil });
      comp.ngOnInit();

      expect(esferaService.query).toHaveBeenCalled();
      expect(esferaService.addEsferaToCollectionIfMissing).toHaveBeenCalledWith(
        esferaCollection,
        ...additionalEsferas.map(expect.objectContaining),
      );
      expect(comp.esferasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const servicoContabil: IServicoContabil = { id: 456 };
      const areaContabil: IAreaContabil = { id: 10970 };
      servicoContabil.areaContabil = areaContabil;
      const esfera: IEsfera = { id: 27246 };
      servicoContabil.esfera = esfera;

      activatedRoute.data = of({ servicoContabil });
      comp.ngOnInit();

      expect(comp.areaContabilsSharedCollection).toContain(areaContabil);
      expect(comp.esferasSharedCollection).toContain(esfera);
      expect(comp.servicoContabil).toEqual(servicoContabil);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IServicoContabil>>();
      const servicoContabil = { id: 123 };
      jest.spyOn(servicoContabilFormService, 'getServicoContabil').mockReturnValue(servicoContabil);
      jest.spyOn(servicoContabilService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ servicoContabil });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: servicoContabil }));
      saveSubject.complete();

      // THEN
      expect(servicoContabilFormService.getServicoContabil).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(servicoContabilService.update).toHaveBeenCalledWith(expect.objectContaining(servicoContabil));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IServicoContabil>>();
      const servicoContabil = { id: 123 };
      jest.spyOn(servicoContabilFormService, 'getServicoContabil').mockReturnValue({ id: null });
      jest.spyOn(servicoContabilService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ servicoContabil: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: servicoContabil }));
      saveSubject.complete();

      // THEN
      expect(servicoContabilFormService.getServicoContabil).toHaveBeenCalled();
      expect(servicoContabilService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IServicoContabil>>();
      const servicoContabil = { id: 123 };
      jest.spyOn(servicoContabilService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ servicoContabil });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(servicoContabilService.update).toHaveBeenCalled();
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

    describe('compareEsfera', () => {
      it('Should forward to esferaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(esferaService, 'compareEsfera');
        comp.compareEsfera(entity, entity2);
        expect(esferaService.compareEsfera).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
