import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { GrupoAcessoPadraoService } from '../service/grupo-acesso-padrao.service';
import { IGrupoAcessoPadrao } from '../grupo-acesso-padrao.model';
import { GrupoAcessoPadraoFormService } from './grupo-acesso-padrao-form.service';

import { GrupoAcessoPadraoUpdateComponent } from './grupo-acesso-padrao-update.component';

describe('GrupoAcessoPadrao Management Update Component', () => {
  let comp: GrupoAcessoPadraoUpdateComponent;
  let fixture: ComponentFixture<GrupoAcessoPadraoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let grupoAcessoPadraoFormService: GrupoAcessoPadraoFormService;
  let grupoAcessoPadraoService: GrupoAcessoPadraoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [GrupoAcessoPadraoUpdateComponent],
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
      .overrideTemplate(GrupoAcessoPadraoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(GrupoAcessoPadraoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    grupoAcessoPadraoFormService = TestBed.inject(GrupoAcessoPadraoFormService);
    grupoAcessoPadraoService = TestBed.inject(GrupoAcessoPadraoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const grupoAcessoPadrao: IGrupoAcessoPadrao = { id: 456 };

      activatedRoute.data = of({ grupoAcessoPadrao });
      comp.ngOnInit();

      expect(comp.grupoAcessoPadrao).toEqual(grupoAcessoPadrao);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGrupoAcessoPadrao>>();
      const grupoAcessoPadrao = { id: 123 };
      jest.spyOn(grupoAcessoPadraoFormService, 'getGrupoAcessoPadrao').mockReturnValue(grupoAcessoPadrao);
      jest.spyOn(grupoAcessoPadraoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ grupoAcessoPadrao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: grupoAcessoPadrao }));
      saveSubject.complete();

      // THEN
      expect(grupoAcessoPadraoFormService.getGrupoAcessoPadrao).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(grupoAcessoPadraoService.update).toHaveBeenCalledWith(expect.objectContaining(grupoAcessoPadrao));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGrupoAcessoPadrao>>();
      const grupoAcessoPadrao = { id: 123 };
      jest.spyOn(grupoAcessoPadraoFormService, 'getGrupoAcessoPadrao').mockReturnValue({ id: null });
      jest.spyOn(grupoAcessoPadraoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ grupoAcessoPadrao: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: grupoAcessoPadrao }));
      saveSubject.complete();

      // THEN
      expect(grupoAcessoPadraoFormService.getGrupoAcessoPadrao).toHaveBeenCalled();
      expect(grupoAcessoPadraoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGrupoAcessoPadrao>>();
      const grupoAcessoPadrao = { id: 123 };
      jest.spyOn(grupoAcessoPadraoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ grupoAcessoPadrao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(grupoAcessoPadraoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
