import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { AtividadeEmpresaDetailComponent } from './atividade-empresa-detail.component';

describe('AtividadeEmpresa Management Detail Component', () => {
  let comp: AtividadeEmpresaDetailComponent;
  let fixture: ComponentFixture<AtividadeEmpresaDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AtividadeEmpresaDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: AtividadeEmpresaDetailComponent,
              resolve: { atividadeEmpresa: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(AtividadeEmpresaDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AtividadeEmpresaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load atividadeEmpresa on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', AtividadeEmpresaDetailComponent);

      // THEN
      expect(instance.atividadeEmpresa()).toEqual(expect.objectContaining({ id: 123 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
