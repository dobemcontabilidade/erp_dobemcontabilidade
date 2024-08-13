import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { EmpresaService } from 'app/entities/empresa/service/empresa.service';
import { IContador } from 'app/entities/contador/contador.model';
import { ContadorService } from 'app/entities/contador/service/contador.service';
import { IFluxoModelo } from 'app/entities/fluxo-modelo/fluxo-modelo.model';
import { FluxoModeloService } from 'app/entities/fluxo-modelo/service/fluxo-modelo.service';
import { IOrdemServico } from '../ordem-servico.model';
import { OrdemServicoService } from '../service/ordem-servico.service';
import { OrdemServicoFormService } from './ordem-servico-form.service';

import { OrdemServicoUpdateComponent } from './ordem-servico-update.component';

describe('OrdemServico Management Update Component', () => {
  let comp: OrdemServicoUpdateComponent;
  let fixture: ComponentFixture<OrdemServicoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let ordemServicoFormService: OrdemServicoFormService;
  let ordemServicoService: OrdemServicoService;
  let empresaService: EmpresaService;
  let contadorService: ContadorService;
  let fluxoModeloService: FluxoModeloService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [OrdemServicoUpdateComponent],
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
      .overrideTemplate(OrdemServicoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(OrdemServicoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    ordemServicoFormService = TestBed.inject(OrdemServicoFormService);
    ordemServicoService = TestBed.inject(OrdemServicoService);
    empresaService = TestBed.inject(EmpresaService);
    contadorService = TestBed.inject(ContadorService);
    fluxoModeloService = TestBed.inject(FluxoModeloService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Empresa query and add missing value', () => {
      const ordemServico: IOrdemServico = { id: 456 };
      const empresa: IEmpresa = { id: 23697 };
      ordemServico.empresa = empresa;

      const empresaCollection: IEmpresa[] = [{ id: 11614 }];
      jest.spyOn(empresaService, 'query').mockReturnValue(of(new HttpResponse({ body: empresaCollection })));
      const additionalEmpresas = [empresa];
      const expectedCollection: IEmpresa[] = [...additionalEmpresas, ...empresaCollection];
      jest.spyOn(empresaService, 'addEmpresaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ ordemServico });
      comp.ngOnInit();

      expect(empresaService.query).toHaveBeenCalled();
      expect(empresaService.addEmpresaToCollectionIfMissing).toHaveBeenCalledWith(
        empresaCollection,
        ...additionalEmpresas.map(expect.objectContaining),
      );
      expect(comp.empresasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Contador query and add missing value', () => {
      const ordemServico: IOrdemServico = { id: 456 };
      const contador: IContador = { id: 31177 };
      ordemServico.contador = contador;

      const contadorCollection: IContador[] = [{ id: 11171 }];
      jest.spyOn(contadorService, 'query').mockReturnValue(of(new HttpResponse({ body: contadorCollection })));
      const additionalContadors = [contador];
      const expectedCollection: IContador[] = [...additionalContadors, ...contadorCollection];
      jest.spyOn(contadorService, 'addContadorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ ordemServico });
      comp.ngOnInit();

      expect(contadorService.query).toHaveBeenCalled();
      expect(contadorService.addContadorToCollectionIfMissing).toHaveBeenCalledWith(
        contadorCollection,
        ...additionalContadors.map(expect.objectContaining),
      );
      expect(comp.contadorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call FluxoModelo query and add missing value', () => {
      const ordemServico: IOrdemServico = { id: 456 };
      const fluxoModelo: IFluxoModelo = { id: 6572 };
      ordemServico.fluxoModelo = fluxoModelo;

      const fluxoModeloCollection: IFluxoModelo[] = [{ id: 29929 }];
      jest.spyOn(fluxoModeloService, 'query').mockReturnValue(of(new HttpResponse({ body: fluxoModeloCollection })));
      const additionalFluxoModelos = [fluxoModelo];
      const expectedCollection: IFluxoModelo[] = [...additionalFluxoModelos, ...fluxoModeloCollection];
      jest.spyOn(fluxoModeloService, 'addFluxoModeloToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ ordemServico });
      comp.ngOnInit();

      expect(fluxoModeloService.query).toHaveBeenCalled();
      expect(fluxoModeloService.addFluxoModeloToCollectionIfMissing).toHaveBeenCalledWith(
        fluxoModeloCollection,
        ...additionalFluxoModelos.map(expect.objectContaining),
      );
      expect(comp.fluxoModelosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const ordemServico: IOrdemServico = { id: 456 };
      const empresa: IEmpresa = { id: 17409 };
      ordemServico.empresa = empresa;
      const contador: IContador = { id: 10849 };
      ordemServico.contador = contador;
      const fluxoModelo: IFluxoModelo = { id: 4647 };
      ordemServico.fluxoModelo = fluxoModelo;

      activatedRoute.data = of({ ordemServico });
      comp.ngOnInit();

      expect(comp.empresasSharedCollection).toContain(empresa);
      expect(comp.contadorsSharedCollection).toContain(contador);
      expect(comp.fluxoModelosSharedCollection).toContain(fluxoModelo);
      expect(comp.ordemServico).toEqual(ordemServico);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrdemServico>>();
      const ordemServico = { id: 123 };
      jest.spyOn(ordemServicoFormService, 'getOrdemServico').mockReturnValue(ordemServico);
      jest.spyOn(ordemServicoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ordemServico });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: ordemServico }));
      saveSubject.complete();

      // THEN
      expect(ordemServicoFormService.getOrdemServico).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(ordemServicoService.update).toHaveBeenCalledWith(expect.objectContaining(ordemServico));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrdemServico>>();
      const ordemServico = { id: 123 };
      jest.spyOn(ordemServicoFormService, 'getOrdemServico').mockReturnValue({ id: null });
      jest.spyOn(ordemServicoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ordemServico: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: ordemServico }));
      saveSubject.complete();

      // THEN
      expect(ordemServicoFormService.getOrdemServico).toHaveBeenCalled();
      expect(ordemServicoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrdemServico>>();
      const ordemServico = { id: 123 };
      jest.spyOn(ordemServicoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ordemServico });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(ordemServicoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareEmpresa', () => {
      it('Should forward to empresaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(empresaService, 'compareEmpresa');
        comp.compareEmpresa(entity, entity2);
        expect(empresaService.compareEmpresa).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareContador', () => {
      it('Should forward to contadorService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(contadorService, 'compareContador');
        comp.compareContador(entity, entity2);
        expect(contadorService.compareContador).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareFluxoModelo', () => {
      it('Should forward to fluxoModeloService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(fluxoModeloService, 'compareFluxoModelo');
        comp.compareFluxoModelo(entity, entity2);
        expect(fluxoModeloService.compareFluxoModelo).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
