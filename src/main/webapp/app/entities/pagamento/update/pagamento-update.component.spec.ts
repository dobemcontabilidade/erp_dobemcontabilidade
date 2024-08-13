import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IAssinaturaEmpresa } from 'app/entities/assinatura-empresa/assinatura-empresa.model';
import { AssinaturaEmpresaService } from 'app/entities/assinatura-empresa/service/assinatura-empresa.service';
import { PagamentoService } from '../service/pagamento.service';
import { IPagamento } from '../pagamento.model';
import { PagamentoFormService } from './pagamento-form.service';

import { PagamentoUpdateComponent } from './pagamento-update.component';

describe('Pagamento Management Update Component', () => {
  let comp: PagamentoUpdateComponent;
  let fixture: ComponentFixture<PagamentoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let pagamentoFormService: PagamentoFormService;
  let pagamentoService: PagamentoService;
  let assinaturaEmpresaService: AssinaturaEmpresaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [PagamentoUpdateComponent],
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
      .overrideTemplate(PagamentoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PagamentoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    pagamentoFormService = TestBed.inject(PagamentoFormService);
    pagamentoService = TestBed.inject(PagamentoService);
    assinaturaEmpresaService = TestBed.inject(AssinaturaEmpresaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call AssinaturaEmpresa query and add missing value', () => {
      const pagamento: IPagamento = { id: 456 };
      const assinaturaEmpresa: IAssinaturaEmpresa = { id: 19188 };
      pagamento.assinaturaEmpresa = assinaturaEmpresa;

      const assinaturaEmpresaCollection: IAssinaturaEmpresa[] = [{ id: 25253 }];
      jest.spyOn(assinaturaEmpresaService, 'query').mockReturnValue(of(new HttpResponse({ body: assinaturaEmpresaCollection })));
      const additionalAssinaturaEmpresas = [assinaturaEmpresa];
      const expectedCollection: IAssinaturaEmpresa[] = [...additionalAssinaturaEmpresas, ...assinaturaEmpresaCollection];
      jest.spyOn(assinaturaEmpresaService, 'addAssinaturaEmpresaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ pagamento });
      comp.ngOnInit();

      expect(assinaturaEmpresaService.query).toHaveBeenCalled();
      expect(assinaturaEmpresaService.addAssinaturaEmpresaToCollectionIfMissing).toHaveBeenCalledWith(
        assinaturaEmpresaCollection,
        ...additionalAssinaturaEmpresas.map(expect.objectContaining),
      );
      expect(comp.assinaturaEmpresasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const pagamento: IPagamento = { id: 456 };
      const assinaturaEmpresa: IAssinaturaEmpresa = { id: 32736 };
      pagamento.assinaturaEmpresa = assinaturaEmpresa;

      activatedRoute.data = of({ pagamento });
      comp.ngOnInit();

      expect(comp.assinaturaEmpresasSharedCollection).toContain(assinaturaEmpresa);
      expect(comp.pagamento).toEqual(pagamento);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPagamento>>();
      const pagamento = { id: 123 };
      jest.spyOn(pagamentoFormService, 'getPagamento').mockReturnValue(pagamento);
      jest.spyOn(pagamentoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pagamento });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pagamento }));
      saveSubject.complete();

      // THEN
      expect(pagamentoFormService.getPagamento).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(pagamentoService.update).toHaveBeenCalledWith(expect.objectContaining(pagamento));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPagamento>>();
      const pagamento = { id: 123 };
      jest.spyOn(pagamentoFormService, 'getPagamento').mockReturnValue({ id: null });
      jest.spyOn(pagamentoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pagamento: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pagamento }));
      saveSubject.complete();

      // THEN
      expect(pagamentoFormService.getPagamento).toHaveBeenCalled();
      expect(pagamentoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPagamento>>();
      const pagamento = { id: 123 };
      jest.spyOn(pagamentoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pagamento });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(pagamentoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareAssinaturaEmpresa', () => {
      it('Should forward to assinaturaEmpresaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(assinaturaEmpresaService, 'compareAssinaturaEmpresa');
        comp.compareAssinaturaEmpresa(entity, entity2);
        expect(assinaturaEmpresaService.compareAssinaturaEmpresa).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
