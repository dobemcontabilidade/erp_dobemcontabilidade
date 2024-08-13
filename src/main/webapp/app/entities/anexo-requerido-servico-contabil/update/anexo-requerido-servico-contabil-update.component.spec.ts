import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IServicoContabil } from 'app/entities/servico-contabil/servico-contabil.model';
import { ServicoContabilService } from 'app/entities/servico-contabil/service/servico-contabil.service';
import { IAnexoRequerido } from 'app/entities/anexo-requerido/anexo-requerido.model';
import { AnexoRequeridoService } from 'app/entities/anexo-requerido/service/anexo-requerido.service';
import { IAnexoRequeridoServicoContabil } from '../anexo-requerido-servico-contabil.model';
import { AnexoRequeridoServicoContabilService } from '../service/anexo-requerido-servico-contabil.service';
import { AnexoRequeridoServicoContabilFormService } from './anexo-requerido-servico-contabil-form.service';

import { AnexoRequeridoServicoContabilUpdateComponent } from './anexo-requerido-servico-contabil-update.component';

describe('AnexoRequeridoServicoContabil Management Update Component', () => {
  let comp: AnexoRequeridoServicoContabilUpdateComponent;
  let fixture: ComponentFixture<AnexoRequeridoServicoContabilUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let anexoRequeridoServicoContabilFormService: AnexoRequeridoServicoContabilFormService;
  let anexoRequeridoServicoContabilService: AnexoRequeridoServicoContabilService;
  let servicoContabilService: ServicoContabilService;
  let anexoRequeridoService: AnexoRequeridoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AnexoRequeridoServicoContabilUpdateComponent],
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
      .overrideTemplate(AnexoRequeridoServicoContabilUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AnexoRequeridoServicoContabilUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    anexoRequeridoServicoContabilFormService = TestBed.inject(AnexoRequeridoServicoContabilFormService);
    anexoRequeridoServicoContabilService = TestBed.inject(AnexoRequeridoServicoContabilService);
    servicoContabilService = TestBed.inject(ServicoContabilService);
    anexoRequeridoService = TestBed.inject(AnexoRequeridoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call ServicoContabil query and add missing value', () => {
      const anexoRequeridoServicoContabil: IAnexoRequeridoServicoContabil = { id: 456 };
      const servicoContabil: IServicoContabil = { id: 15313 };
      anexoRequeridoServicoContabil.servicoContabil = servicoContabil;

      const servicoContabilCollection: IServicoContabil[] = [{ id: 22085 }];
      jest.spyOn(servicoContabilService, 'query').mockReturnValue(of(new HttpResponse({ body: servicoContabilCollection })));
      const additionalServicoContabils = [servicoContabil];
      const expectedCollection: IServicoContabil[] = [...additionalServicoContabils, ...servicoContabilCollection];
      jest.spyOn(servicoContabilService, 'addServicoContabilToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ anexoRequeridoServicoContabil });
      comp.ngOnInit();

      expect(servicoContabilService.query).toHaveBeenCalled();
      expect(servicoContabilService.addServicoContabilToCollectionIfMissing).toHaveBeenCalledWith(
        servicoContabilCollection,
        ...additionalServicoContabils.map(expect.objectContaining),
      );
      expect(comp.servicoContabilsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call AnexoRequerido query and add missing value', () => {
      const anexoRequeridoServicoContabil: IAnexoRequeridoServicoContabil = { id: 456 };
      const anexoRequerido: IAnexoRequerido = { id: 9528 };
      anexoRequeridoServicoContabil.anexoRequerido = anexoRequerido;

      const anexoRequeridoCollection: IAnexoRequerido[] = [{ id: 30010 }];
      jest.spyOn(anexoRequeridoService, 'query').mockReturnValue(of(new HttpResponse({ body: anexoRequeridoCollection })));
      const additionalAnexoRequeridos = [anexoRequerido];
      const expectedCollection: IAnexoRequerido[] = [...additionalAnexoRequeridos, ...anexoRequeridoCollection];
      jest.spyOn(anexoRequeridoService, 'addAnexoRequeridoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ anexoRequeridoServicoContabil });
      comp.ngOnInit();

      expect(anexoRequeridoService.query).toHaveBeenCalled();
      expect(anexoRequeridoService.addAnexoRequeridoToCollectionIfMissing).toHaveBeenCalledWith(
        anexoRequeridoCollection,
        ...additionalAnexoRequeridos.map(expect.objectContaining),
      );
      expect(comp.anexoRequeridosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const anexoRequeridoServicoContabil: IAnexoRequeridoServicoContabil = { id: 456 };
      const servicoContabil: IServicoContabil = { id: 8440 };
      anexoRequeridoServicoContabil.servicoContabil = servicoContabil;
      const anexoRequerido: IAnexoRequerido = { id: 16835 };
      anexoRequeridoServicoContabil.anexoRequerido = anexoRequerido;

      activatedRoute.data = of({ anexoRequeridoServicoContabil });
      comp.ngOnInit();

      expect(comp.servicoContabilsSharedCollection).toContain(servicoContabil);
      expect(comp.anexoRequeridosSharedCollection).toContain(anexoRequerido);
      expect(comp.anexoRequeridoServicoContabil).toEqual(anexoRequeridoServicoContabil);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAnexoRequeridoServicoContabil>>();
      const anexoRequeridoServicoContabil = { id: 123 };
      jest
        .spyOn(anexoRequeridoServicoContabilFormService, 'getAnexoRequeridoServicoContabil')
        .mockReturnValue(anexoRequeridoServicoContabil);
      jest.spyOn(anexoRequeridoServicoContabilService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ anexoRequeridoServicoContabil });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: anexoRequeridoServicoContabil }));
      saveSubject.complete();

      // THEN
      expect(anexoRequeridoServicoContabilFormService.getAnexoRequeridoServicoContabil).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(anexoRequeridoServicoContabilService.update).toHaveBeenCalledWith(expect.objectContaining(anexoRequeridoServicoContabil));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAnexoRequeridoServicoContabil>>();
      const anexoRequeridoServicoContabil = { id: 123 };
      jest.spyOn(anexoRequeridoServicoContabilFormService, 'getAnexoRequeridoServicoContabil').mockReturnValue({ id: null });
      jest.spyOn(anexoRequeridoServicoContabilService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ anexoRequeridoServicoContabil: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: anexoRequeridoServicoContabil }));
      saveSubject.complete();

      // THEN
      expect(anexoRequeridoServicoContabilFormService.getAnexoRequeridoServicoContabil).toHaveBeenCalled();
      expect(anexoRequeridoServicoContabilService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAnexoRequeridoServicoContabil>>();
      const anexoRequeridoServicoContabil = { id: 123 };
      jest.spyOn(anexoRequeridoServicoContabilService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ anexoRequeridoServicoContabil });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(anexoRequeridoServicoContabilService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareServicoContabil', () => {
      it('Should forward to servicoContabilService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(servicoContabilService, 'compareServicoContabil');
        comp.compareServicoContabil(entity, entity2);
        expect(servicoContabilService.compareServicoContabil).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareAnexoRequerido', () => {
      it('Should forward to anexoRequeridoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(anexoRequeridoService, 'compareAnexoRequerido');
        comp.compareAnexoRequerido(entity, entity2);
        expect(anexoRequeridoService.compareAnexoRequerido).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
