import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { EscolaridadePessoaDetailComponent } from './escolaridade-pessoa-detail.component';

describe('EscolaridadePessoa Management Detail Component', () => {
  let comp: EscolaridadePessoaDetailComponent;
  let fixture: ComponentFixture<EscolaridadePessoaDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EscolaridadePessoaDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: EscolaridadePessoaDetailComponent,
              resolve: { escolaridadePessoa: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(EscolaridadePessoaDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EscolaridadePessoaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load escolaridadePessoa on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', EscolaridadePessoaDetailComponent);

      // THEN
      expect(instance.escolaridadePessoa()).toEqual(expect.objectContaining({ id: 123 }));
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
