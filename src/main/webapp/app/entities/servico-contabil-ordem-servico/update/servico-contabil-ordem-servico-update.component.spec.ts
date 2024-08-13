import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IServicoContabil } from 'app/entities/servico-contabil/servico-contabil.model';
import { ServicoContabilService } from 'app/entities/servico-contabil/service/servico-contabil.service';
import { IOrdemServico } from 'app/entities/ordem-servico/ordem-servico.model';
import { OrdemServicoService } from 'app/entities/ordem-servico/service/ordem-servico.service';
import { IServicoContabilOrdemServico } from '../servico-contabil-ordem-servico.model';
import { ServicoContabilOrdemServicoService } from '../service/servico-contabil-ordem-servico.service';
import { ServicoContabilOrdemServicoFormService } from './servico-contabil-ordem-servico-form.service';

import { ServicoContabilOrdemServicoUpdateComponent } from './servico-contabil-ordem-servico-update.component';

describe('ServicoContabilOrdemServico Management Update Component', () => {
  let comp: ServicoContabilOrdemServicoUpdateComponent;
  let fixture: ComponentFixture<ServicoContabilOrdemServicoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let servicoContabilOrdemServicoFormService: ServicoContabilOrdemServicoFormService;
  let servicoContabilOrdemServicoService: ServicoContabilOrdemServicoService;
  let servicoContabilService: ServicoContabilService;
  let ordemServicoService: OrdemServicoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ServicoContabilOrdemServicoUpdateComponent],
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
      .overrideTemplate(ServicoContabilOrdemServicoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ServicoContabilOrdemServicoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    servicoContabilOrdemServicoFormService = TestBed.inject(ServicoContabilOrdemServicoFormService);
    servicoContabilOrdemServicoService = TestBed.inject(ServicoContabilOrdemServicoService);
    servicoContabilService = TestBed.inject(ServicoContabilService);
    ordemServicoService = TestBed.inject(OrdemServicoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call ServicoContabil query and add missing value', () => {
      const servicoContabilOrdemServico: IServicoContabilOrdemServico = { id: 456 };
      const servicoContabil: IServicoContabil = { id: 22466 };
      servicoContabilOrdemServico.servicoContabil = servicoContabil;

      const servicoContabilCollection: IServicoContabil[] = [{ id: 3849 }];
      jest.spyOn(servicoContabilService, 'query').mockReturnValue(of(new HttpResponse({ body: servicoContabilCollection })));
      const additionalServicoContabils = [servicoContabil];
      const expectedCollection: IServicoContabil[] = [...additionalServicoContabils, ...servicoContabilCollection];
      jest.spyOn(servicoContabilService, 'addServicoContabilToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ servicoContabilOrdemServico });
      comp.ngOnInit();

      expect(servicoContabilService.query).toHaveBeenCalled();
      expect(servicoContabilService.addServicoContabilToCollectionIfMissing).toHaveBeenCalledWith(
        servicoContabilCollection,
        ...additionalServicoContabils.map(expect.objectContaining),
      );
      expect(comp.servicoContabilsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call OrdemServico query and add missing value', () => {
      const servicoContabilOrdemServico: IServicoContabilOrdemServico = { id: 456 };
      const ordemServico: IOrdemServico = { id: 23492 };
      servicoContabilOrdemServico.ordemServico = ordemServico;

      const ordemServicoCollection: IOrdemServico[] = [{ id: 21771 }];
      jest.spyOn(ordemServicoService, 'query').mockReturnValue(of(new HttpResponse({ body: ordemServicoCollection })));
      const additionalOrdemServicos = [ordemServico];
      const expectedCollection: IOrdemServico[] = [...additionalOrdemServicos, ...ordemServicoCollection];
      jest.spyOn(ordemServicoService, 'addOrdemServicoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ servicoContabilOrdemServico });
      comp.ngOnInit();

      expect(ordemServicoService.query).toHaveBeenCalled();
      expect(ordemServicoService.addOrdemServicoToCollectionIfMissing).toHaveBeenCalledWith(
        ordemServicoCollection,
        ...additionalOrdemServicos.map(expect.objectContaining),
      );
      expect(comp.ordemServicosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const servicoContabilOrdemServico: IServicoContabilOrdemServico = { id: 456 };
      const servicoContabil: IServicoContabil = { id: 849 };
      servicoContabilOrdemServico.servicoContabil = servicoContabil;
      const ordemServico: IOrdemServico = { id: 15724 };
      servicoContabilOrdemServico.ordemServico = ordemServico;

      activatedRoute.data = of({ servicoContabilOrdemServico });
      comp.ngOnInit();

      expect(comp.servicoContabilsSharedCollection).toContain(servicoContabil);
      expect(comp.ordemServicosSharedCollection).toContain(ordemServico);
      expect(comp.servicoContabilOrdemServico).toEqual(servicoContabilOrdemServico);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IServicoContabilOrdemServico>>();
      const servicoContabilOrdemServico = { id: 123 };
      jest.spyOn(servicoContabilOrdemServicoFormService, 'getServicoContabilOrdemServico').mockReturnValue(servicoContabilOrdemServico);
      jest.spyOn(servicoContabilOrdemServicoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ servicoContabilOrdemServico });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: servicoContabilOrdemServico }));
      saveSubject.complete();

      // THEN
      expect(servicoContabilOrdemServicoFormService.getServicoContabilOrdemServico).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(servicoContabilOrdemServicoService.update).toHaveBeenCalledWith(expect.objectContaining(servicoContabilOrdemServico));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IServicoContabilOrdemServico>>();
      const servicoContabilOrdemServico = { id: 123 };
      jest.spyOn(servicoContabilOrdemServicoFormService, 'getServicoContabilOrdemServico').mockReturnValue({ id: null });
      jest.spyOn(servicoContabilOrdemServicoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ servicoContabilOrdemServico: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: servicoContabilOrdemServico }));
      saveSubject.complete();

      // THEN
      expect(servicoContabilOrdemServicoFormService.getServicoContabilOrdemServico).toHaveBeenCalled();
      expect(servicoContabilOrdemServicoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IServicoContabilOrdemServico>>();
      const servicoContabilOrdemServico = { id: 123 };
      jest.spyOn(servicoContabilOrdemServicoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ servicoContabilOrdemServico });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(servicoContabilOrdemServicoService.update).toHaveBeenCalled();
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

    describe('compareOrdemServico', () => {
      it('Should forward to ordemServicoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(ordemServicoService, 'compareOrdemServico');
        comp.compareOrdemServico(entity, entity2);
        expect(ordemServicoService.compareOrdemServico).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
